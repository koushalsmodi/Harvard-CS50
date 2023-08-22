def main():
    print(fuel_percent())

def fuel_percent():
    while True: # loop as long as true
        try:
            fraction = input('Fraction: ') # getting input and splitting the numerator and denominator
            x, y = fraction.split('/')

            if int(x) > int(y): # if x is greater than y , enter a valid fraction
                print('Please enter a proper fraction.')
                continue
            conversion = int(x) / int(y) * 100 # percentage formation
            percent = round(conversion) # round the percentage

            if percent <=1: # if percent less than 1 , empty sign
                return 'E'
            elif percent >= 99: # if percent greater than 99 , full sign
                return 'F'
            else:
                return str(percent) + '%' # return the percentage with % sign

        except ValueError: # value error
            pass

        except ZeroDivisionError: # zero division error
            pass

main()
