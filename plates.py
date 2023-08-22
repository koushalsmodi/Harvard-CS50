# plate :
# start with 2 letters
# if len(plate) >= 2 and len(plate) == 6
# plate.endswith numbers
# first number cannot be 0
# “No periods, spaces, or punctuation marks are allowed.”


def main():
    plate = input("Plate: ").upper()
    if is_valid(plate): # validation
        print("Valid")
    else:
        print("Invalid")

# if the first 2 characters are alphabets , len of licence plate is less than or equal to 6,
# the digits after the first 2 characters are digits and the starting number is not 0

def is_valid(s):
    if s[0:2].isalpha() and len(s) <= 6 and s[2:].isdigit() and s[2] != '0':
        return True
    else:
        return False
main()