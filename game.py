import random

while True:
    try:
        n = int(input('Level: '))
        break
    except ValueError:
        print('Invalid input. Please enter a valid number.')

number = random.randint(1, n)

while n > 0:
    try:
        guess = int(input('Guess: '))

        if guess > number:
            print('Too large!')
        elif guess < number:
            print('Too small!')
        else:
            print('Just right!')
            break

        n -= 1

    except ValueError:
        print('Invalid input. Please enter a valid number.')
