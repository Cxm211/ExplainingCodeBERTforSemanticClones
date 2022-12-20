from Simplifiedcodebert import myfunc
import csv
import os

INPUT_PATH = "Filtered Python Codes/"

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

def main():
    clone_files = os.listdir(INPUT_PATH)
    for file in clone_files:
            filename = INPUT_PATH + file
            print("Running for " + file)
            flig = get_original_prediction(filename)
            f = open("prediction_result.csv", "a")
            writer = csv.writer(f)
            row = [file, flig]

            writer.writerow(row)
main()