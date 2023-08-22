x, y, z = input('Expression: ').split(' ') # getting input and splitting based on spaces

# y can be + , - ,  * or /
# then printing using f string to format to 1 decimal place as float number
if y == '+':
    add = int(x) + int(z)
    print(f'{add:.1f}')
elif y == '-':
    sub = int(x) - int(z)
    print(f'{sub:.1f}')
elif y == '*':
    mult = int(x) * int(z)
    print(f'{mult:.1f}')
elif y == '/':
    div = int(x) / int(z)
    print(f'{div:.1f}')
else:
    print('unknown')  
