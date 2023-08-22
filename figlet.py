import sys
from pyfiglet import Figlet
import random

figlet = Figlet()

if len(sys.argv) == 3 and sys.argv[1] not in ['-f', '--font']:
    sys.exit('Invalid usage')

if len(sys.argv) == 1:
    f = random.choice(figlet.getFonts())
    figlet.setFont(font=f)
    user = input('Input: ')
    print('Output:', figlet.renderText(user))

elif len(sys.argv) == 3 and sys.argv[1] in ['-f', '--font']:
    figlet.setFont(font=sys.argv[2])
    user = input('Input: ')
    print('Output:',figlet.renderText(user))