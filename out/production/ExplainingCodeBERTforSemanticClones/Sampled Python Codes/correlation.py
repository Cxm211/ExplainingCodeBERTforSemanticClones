import scipy.stats as stats
import sys
import re
def myfunc(x,y):
    #x = [0, 1, 1, 0, 0]
    #y = [-1, 1, 1, 0, 0]
    result = stats.pointbiserialr(x, y)
    #calculate point-biserial correlation
    print(stats.pointbiserialr(x, y))
    return result

def find_numbers(string, ints=True):
    numexp = re.compile(r'[-]?\d[\d]*[\.]?[\d]*') #optional - in front
    # numexp = re.compile(r'[+-]?[0 - 9]+([.][0-9]+)?')
    numbers = numexp.findall(string)
    # numbers = [x.replace(',','') for x in numbers]
    if ints is True:
        return [float(x.replace(',','')) for x in numbers]
    else:
        return numbers


# n = len(sys.argv)
# print("Total arguments passed:", n)
# print("\nArguments passed:", end = " ")
# for i in range(1, n):
#     print(sys.argv[i], end = " ")
a = sys.argv[1]
b = sys.argv[2]
# a = "0.0568597,0.08165522,0.05777734,0.07069201,0.06546229,0.06896505,0.03909395"
# b = "1,1,1,1,1,1,0"
a = find_numbers(a)
b = find_numbers(b)
# p = '[\d]+[.\d]+|[\d]*[.][\d]+|[\d]+'
# a = [s for s in re.findall(p, a)]
# b = [s for s in re.findall(p, b)]
myfunc(a, b)
