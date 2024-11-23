def main():
    text = input('Input: ')
    output = shorten(text)
    print(f"Output: {output}")


def shorten(word):
    vowels = ["a", "e", "i", "o", "u"]
    shortened = []
    for i in word:
        if i.casefold() not in vowels:
            shortened.append(i)
    output = str("".join(shortened))
    return output
main()
