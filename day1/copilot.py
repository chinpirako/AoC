def solveAdventOfCodeDay1(input):
    return sum(map(lambda x: int(x) // 3 - 2, input.splitlines()))

with open('example.txt', 'r') as f:
    print(solveAdventOfCodeDay1(f.read()))