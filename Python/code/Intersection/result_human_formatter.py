import csv

file_name = "result_wheat_m2.csv"
new_file = "human_wheat_m2.csv"
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

def human(file_name,new_file, method):
    with open(file_name,'r') as f:
        reader = csv.reader(f)
        with open(new_file,'w') as new_f:
            writer = csv.writer(new_f)
            for row in reader:
                count =  method('Filtered Python Codes', row[0])
                if row[4] == '':
                    for c in range(count):
                        new_row = [row[0][:-3]]
                        new_row.append(c + 1)
                        new_row.append(1)
                        writer.writerow(new_row)
                else:
                    for c in range(count):
                        flag = 0
                        new_row = [row[0][:-3]]
                        new_row.append(c+1)
                        for i in row[4]:
                            if i != '_':
                                if c == int(i):
                                    new_row.append(1)
                                    writer.writerow(new_row)
                                    flag = 1
                        if flag == 0:
                            new_row.append(0)
                            writer.writerow(new_row)


def seperate_m1m2():
    with open('humanIntuition.csv', 'r') as f:
        with open('humanIntuitionm1.csv', 'w') as m1:
            with open('humanIntuitionm2.csv', 'w') as m2:
                m1 = csv.writer(m1)
                m2 = csv.writer(m2)
                reader = csv.reader(f)
                flag = 1
                for row in reader:
                    if row[1]=='1' and flag==1:
                        flag = 0
                    elif row[1]=='1' and flag==0:
                        flag = 1
                    if flag == 0:
                        print(row)
                        m1.writerow(row)
                    else:
                        m2.writerow(row)

file_name = ["result_wheat_m1.csv","result_wheat_m2.csv","result_culprit_m1.csv","result_culprit_m2.csv"]
new_file = ["human_wheat_m1.csv", "human_wheat_m2.csv", "human_culprit_m1.csv", "human_culprit_m2.csv"]
for i in range(4):
    if i == 0 or i == 2:
        human(file_name[i],new_file[i],getm1linescountfromfile)
    else:
        human(file_name[i], new_file[i], getm2linescountfromfile)

seperate_m1m2()