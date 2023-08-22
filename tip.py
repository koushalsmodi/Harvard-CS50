def main():
    dollars = dollars_to_float(input("How much was the meal? ")) # calling function and the input is str for cost of meal
    percent = percent_to_float(input("What percentage would you like to tip? ")) # calling function and the input is str for tip percent
    tip = dollars * percent # getting tip amount (float value)
    print(f"Leave ${tip:.2f}") # converting the tip amount in 2 decimal places


def dollars_to_float(d):
    d = d.removeprefix('$') # removing prefix of dollar sign
    return float(d) # returning float value of d

def percent_to_float(p):
    p = p.removesuffix('%')  # removing suffix of dollar sign
    p = float(p)/100 # converting str percentage to float and then dividing by 100
    return p # returning value of p

main() # calling main function