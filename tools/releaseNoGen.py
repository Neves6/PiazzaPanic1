import os

env_file = os.getenv('GITHUB_ENV')

file = open(".github/workflows/releaseNumber.txt")
number = file.read()

dotIndex = number.rfind(".") + 1
newNo = int(number[dotIndex:]) + 1

with open(env_file, "a") as myfile:
    myfile.write("STABLE_NAME={}".format(number[:dotIndex] + str(newNo)))


