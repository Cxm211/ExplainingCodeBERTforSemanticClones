import itertools
from Simplifiedcodebert import myfunc
import csv
import copy
import os

INPUT_PATH = "Filtered Python Codes/"

def get_all_combination(code_list):
    mutations = []
    for i in range(len(code_list)):
        mutations += itertools.combinations(code_list, i+1)
    return mutations

def get_original_prediction(filename):
    ori_file = open(filename, "r")
    m1 = []
    m2 = []
    line = ori_file.readline()
    m1.append(line)
    line = ori_file.readline()
    while not "def " in line:
        if line != "" and line != "\n":
            m1.append(line)
        line = ori_file.readline()
    #Read m2
    while line:
        if line != "" and line != "\n":
            m2.append(line)
        line = ori_file.readline()
    #Get the prediction of original file
    mutation_file = open("pythontestfolder/mutation.py", "w")
    for line in m1:
        mutation_file.write(line)
    mutation_file.write('\n')
    for line in m2:
        mutation_file.write(line)
    mutation_file.close()
    #Get prediction result
    prediction_result = myfunc()
    return prediction_result

def get_mutation_prediction_m2(filename, flig):
    ori_file = open(filename, "r")
    m1 = []
    m2 = []
    line = ori_file.readline()
    m1.append(line)
    line = ori_file.readline()
    while not "def " in line:
        if line != "" and line != "\n":
            m1.append(line)
        line = ori_file.readline()
    #Read m2
    while line:
        if line != "" and line != "\n":
            m2.append(line)
        line = ori_file.readline()
    #generate mutations for m2
    mutations = get_all_combination(m2)
    if flig == 0:
        count = 0
        for mutation in reversed(mutations):
            mutation_filem2 = open("pythontestfolder/mutation.py", "w")
            for line in mutation:
                mutation_filem2.write(line)
            for line in m1:
                mutation_filem2.write(line)
            mutation_filem2.close()
            result = myfunc()
            has_culprit = False
            num_of_lines = 0
            if result == 1:
                has_culprit = True
                culprit = []
                num_of_lines = len(m2) - len(mutation)
                ori_m2 = copy.deepcopy(m2)
                index_list = ""
                for line in mutation:
                    m2.remove(line)
                for line in m2:
                    index = ori_m2.index(line)
                    index_list += str(index)
                    index_list += str("_")
                return filename, has_culprit, num_of_lines, count, index_list, m2
            else:
                count += 1
        return filename, has_culprit, num_of_lines, count, "", list()
    if flig == 1:
        count = 0
        for mutation in reversed(mutations):
            mutation_filem2 = open("pythontestfolder/mutation.py", "w")
            for line in mutation:
                mutation_filem2.write(line)
            for line in m1:
                mutation_filem2.write(line)
            mutation_filem2.close()
            result = myfunc()
            wheat = 0
            num_of_lines = 0
            if result == 0:
                wheat = 1
                wheat = []
                num_of_lines = len(m2) - len(mutation)
                ori_m2 = copy.deepcopy(m2)
                index_list = ""
                for line in mutation:
                    m2.remove(line)
                for line in m2:
                    index = ori_m2.index(line)
                    index_list += str(index)
                    index_list += str("_")
                #Check sufficient
                mutation_filem2 = open("pythontestfolder/mutation.py", "w")
                for line in m2:
                    mutation_filem2.write(line)
                for line in m1:
                    mutation_filem2.write(line)
                mutation_filem2.close()
                result = myfunc()
                if result == 1:
                    wheat = 2
                return filename, wheat, num_of_lines, count, index_list, m2
            else:
                count += 1
        return filename, wheat, num_of_lines, count, "", list()


def main_m2():
    clone_files = os.listdir(INPUT_PATH)
    for file in clone_files:
            filename = INPUT_PATH + file
            print("Running for " + file)
            flig = get_original_prediction(filename)
            if flig == 0:
                f = open("result_culprit_m2.csv", "a")
                writer = csv.writer(f)
                filename, has_culprit, num_of_lines, count, index_list, culprit = get_mutation_prediction_m2(filename, flig)
                if has_culprit:
                    row = [file, "Has culprit", num_of_lines, count, index_list]
                else:
                    row = [file, "No culprit", num_of_lines, count, index_list]
                for line in culprit:
                    row.append(line)
                print(row)
                writer.writerow(row)
            if flig == 1:
                f = open("result_wheat_m2.csv", "a")
                writer = csv.writer(f)
                filename, wheat, num_of_lines, count, index_list, wheats = get_mutation_prediction_m2(filename, flig)
                if wheat == 0 :
                    row = [file, "No wheat", num_of_lines, count, index_list]
                elif wheat == 1:
                    row = [file, "Has necessary only wheat", num_of_lines, count, index_list]
                else:
                    row = [file, "Has necessary and sufficient wheat", num_of_lines, count, index_list]
                for line in wheats:
                    row.append(line)
                print(row)
                writer.writerow(row)
main_m2()