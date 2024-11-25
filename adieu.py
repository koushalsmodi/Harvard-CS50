import inflect
import inflect
p = inflect.engine()

mylist = list()

while True:
    try:
        user = input('Name: ').strip().title()
        mylist.append(user)

    except EOFError:
        print()
        print('Adieu, adieu, to', p.join(mylist))
        break
