def main():
    x = taqueria() # calling function and storing in x and printing total cost
    print(x)

def taqueria():
    total = 0.0 # initializing total to 0
    # dictionary of items and prices
    menu = {
        "Baja Taco": 4.00,
        "Burrito": 7.50,
        "Bowl": 8.50,
        "Nachos": 11.00,
        "Quesadilla": 8.50,
        "Super Burrito": 8.50,
        "Super Quesadilla": 9.50,
        "Taco": 3.00,
        "Tortilla Salad": 8.00
    }

    while True: # loop as long as control d
        try:
            item = input('Item: ').title() # get the user input and convert to title case since keys are titlecased
            if item in menu: # if the item is a key in the menu dictionary
                total += menu.get(item) # get the value of the item and add to the total
                print(f'Total: ${total:.2f}') # total converted to 2 dps

        except EOFError:  # control d handling
            break
        except KeyError: # if key is not present, pass
            pass

    return total

main()
