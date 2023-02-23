file = open("../.github/workflows/releaseNumber.txt")
number = file.read()

dotIndex = number.rfind(".") + 1
newNo = int(number[dotIndex:]) + 1

print (number[:dotIndex] + str(dotIndex))

