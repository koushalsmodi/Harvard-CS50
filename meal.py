def main():
    # taking time as input
    time = input('What time is it? ')
    # converting time here
    c_time = convert(time)
    if 7 <= c_time <= 8:
        print('breakfast time')
    elif 12 <= c_time <= 13:
        print('lunch time')
    elif 18 <= c_time <= 19:
        print('dinner time')

def convert(time):
    hours, minutes = time.split(":")
    hours = float(hours) + (float(minutes) / 60)
    return hours

if __name__ == "__main__":
    main()
