import requests
import sys # importing


def main():
    if len(sys.argv) == 2: # if the length of the command line is 2
        try:
            n = float(sys.argv[1]) # try to convert the second argument that is at index 1 to a floating point number
            print(bitcoin_price(n)) # pass n to the function
        except ValueError:
            sys.exit('Command-line argument is not a number') # if not integer
    else:

        sys.exit('Missing command-line argument') # else exit with the message

def bitcoin_price(n):
    try:
        response = requests.get('https://api.coindesk.com/v1/bpi/currentprice.json') # connect to the coin desk website
        result = response.json() # get the information in the json format
        price = result['bpi']['USD']['rate_float'] # penetrate inside the dictionaries until we reach rate_float
        amount = price * n # multiply the user entered value by the price obtained from the list of dictionaries
        print(f'${amount:,.4f}') # convert amount to 4 decimal places

    except requests.RequestException:
        return None # any ambiguous exception that occurred while handling the request
main()





