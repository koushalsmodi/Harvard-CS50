# convert camelCase to snake case
# name = name
# firstName = first_name
# preferredFirstName = preferred_first_name

def main():
    text = input('camelCase: ')
    print('snake_case:',conversion(text))

def conversion(pText):
    result = ''
    for i in pText:
        if i.isupper():
            result += '_' + i.lower()
        else:
            result += i
    return result
main()
