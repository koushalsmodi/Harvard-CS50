def main():
    # taking time as input
    time = input('What time is it? ')
    # converting time here
    c_time = convert(time)
    # between 7 and 8 am inclusive, breakfast time
    if 7 <= c_time <= 8:
        print('breakfast time')
    # between 12 and 1 pm inclusive, lunch time
    elif 12 <= c_time <= 13:
        print('lunch time')
    # between 6 and 7 pm inclusive, dinner time
    elif 18 <= c_time <= 19:
        print('dinner time')

def convert(time):
    # converting time by splitting hours and minutes
    hours, minutes = time.split(":")
    hours = float(hours) + (float(minutes) / 60)
    return hours

if __name__ == "__main__":
    main()
