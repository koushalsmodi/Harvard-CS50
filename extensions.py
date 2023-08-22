user = input('Name of a file: ').casefold().strip() # case insensitivity and removing spaces

# using str method endswith to check for ending type of file and returning appropraite file's media type
if user.endswith('.gif'):
    print('image/gif')
elif user.endswith('.jpg') or user.endswith('.jpeg'):
    print('image/jpeg')
elif user.endswith('.png'):
    print('image/png')
elif user.endswith('.pdf'):
    print('application/pdf')
elif user.endswith('.txt'):
    print('text/plain')
elif user.endswith('.zip'):
    print('application/zip')
else:
    print('application/octet-stream')


