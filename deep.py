user = input('What is the Answer to the Great Question of Life, the Universe and Everything? ').strip() # get user input

if user == '42' or user == 'forty-two' or user.casefold() == 'forty two': # validation answer to question
    print('Yes')
else:
    print('No')