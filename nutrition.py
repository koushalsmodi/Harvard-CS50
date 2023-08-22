user = input('Item: ').lower() # getting user input
# creating a dictionary with key as the name of the fruit and value as the calories
listing = {
    'apple':130,
    'avocado':50,
    'banana':110,
    'cantaloupe':50,
    'grapefruit':60,
    'grapes':90,
    'honeydew melon': 50,
    'kiwifruit':90,
    'lemon':15,
    'lime':20,
    'nectarine': 60,
    'orange':80,
    'peach':60,
    'pear':100,
    'pineapple':50,
    'plums':70,
    'strawberries':50,
    'sweet cherries':100,
    'tangerine':50,
    'watermelon':80
}

for fruit in listing: # looping over each key in the dictioanry
    if user == fruit: # if the user input matches a key, print the value associated with the key
        print('Calories:', listing[fruit])


