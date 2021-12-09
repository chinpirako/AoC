from itertools import tee
from more_itertools import windowed
import time

def pairwise(iterable):
    "s -> (s0,s1), (s1,s2), (s2, s3), ..."
    a, b = tee(iterable)
    next(b, None)
    return zip(a, b)


start = time.time()
with open('input.txt', 'r') as file:
    int_input = [int(x) for x in file]
    print(sum(a < b for a, b in pairwise(int_input)))
    print(sum(sum(a) < sum(b) for a, b in pairwise(windowed(int_input, 3))))
end = time.time()

print("Time ms:", (end - start) * 1000, "ms")
