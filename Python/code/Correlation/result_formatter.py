import csv

file_name = "result_wheat_m2.csv"
new_file = "formatted_wheat_m2.csv"
def getm1linescountfromfile(folderpath, filename):
    def read_file_java(folderpath, file_name):
        filename = folderpath + "/" + file_name
        ori_file = open(filename, "r")
        m1 = ""
        m2 = ""
        line = ori_file.readline()
        m1 += line
        line = ori_file.readline()
        while not "def " in line:
            if line != "" and line != "\n":
                m1 += line
            line = ori_file.readline()
        # Read m2
        while line:
            if line != "" and line != "\n":
                m2 += line
            line = ori_file.readline()
        return m1, m2

    # generate dissimilar pairs
    pairs = []
    # for file_name in clone_files:
    code_1, code_2 = read_file_java(folderpath, filename)
    pairs.append((code_1, code_2))

    code_1, code_2 = pairs[0]

    import re
    lineendindices = res = [i.start() for i in re.finditer('\n', code_1)]
    linecount = len(lineendindices)

    return linecount

def getm2linescountfromfile(folderpath, filename):
    def read_file_java(folderpath, file_name):
        filename = folderpath + "/" + file_name
        ori_file = open(filename, "r")
        m1 = ""
        m2 = ""
        line = ori_file.readline()
        m1 += line
        line = ori_file.readline()
        while not "def " in line:
            if line != "" and line != "\n":
                m1 += line
            line = ori_file.readline()
        # Read m2
        while line:
            if line != "" and line != "\n":
                m2 += line
            line = ori_file.readline()
        return m1, m2

    # generate dissimilar pairs
    pairs = []
    # for file_name in clone_files:
    code_1, code_2 = read_file_java(folderpath, filename)
    pairs.append((code_1, code_2))

    code_1, code_2 = pairs[0]

    import re
    lineendindices = res = [i.start() for i in re.finditer('\n', code_2)]
    linecount = len(lineendindices)

    return linecount

def main(file_name,new_file, method):
    with open(file_name,'r') as f:
        reader = csv.reader(f)
        with open(new_file,'w') as new_f:
            writer = csv.writer(new_f)
            for row in reader:
                count =  method('Filtered Python Codes', row[0])
                print(count)
                new_row = [row[0][:-3]]

                if row[4] == '':
                    for c in range(count):
                        new_row.append(1)
                    new_row[-1] = 0
                else:
                    new_line = []
                    for c in range(count):
                        new_line.append(0)
                    for i in row[4]:
                        if i != '_':
                            new_line[int(i)] = 1
                    new_row += new_line
                print(new_row)
                writer.writerow(new_row)

file_name = ["result_wheat_m1.csv","result_wheat_m2.csv","result_culprit_m1.csv","result_culprit_m2.csv"]
new_file = ["formatted_wheat_m1.csv", "formatted_wheat_m2.csv", "formatted_culprit_m1.csv", "formatted_culprit_m2.csv"]
for i in range(4):
    if i == 0 or i == 2:
        main(file_name[i],new_file[i],getm1linescountfromfile)
    else:
        main(file_name[i], new_file[i], getm2linescountfromfile)



