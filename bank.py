greeting = input('Greeting: ').strip() # taking greeting input

if greeting.startswith('Hello'): # if starts with hello then nothing to pay
    print('$0')
elif greeting.startswith('H'): # if with h then agree for $20
    print('$20')
else: # if something else, pay $100
    print('$100')