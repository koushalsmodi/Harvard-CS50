def main():
    user = input('Enter a text message: ') # getting user input
    print(convert(user)) # calling the convert function by passing the argument user to it and then printing the return value

def convert(pUser): # creating a function as parameter as pUser
    pUser = pUser.replace(':)', '🙂') # replacing :) with a slightly smiley face
    pUser = pUser.replace(':(', '🙁') # replacing :() with a slightly frowning face
    return pUser # returning the result

main() # calling the main function



