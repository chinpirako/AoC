file = open("input.txt")
file_contents = file.read() 
contents_split = file_contents.splitlines()
points = {} 
for line in contents_split: 
    start, end = [x.split(',') for x in line.split(' -> ')] 
    x1, y1 = int(start[0]), int(start[1]) 
    x2, y2 = int(end[0]), int(end[1])
    if x1 == x2:  # vertical line
        start_point = f"{x1},{y1}"
        points[start_point] = 1 if start_point not in points.keys() else points[start_point] + 1
        temp = y1
        while temp != y2:
            temp = temp + 1 if temp < y2 else temp - 1
            point = f"{x1},{temp}"
            points[point] = 1 if point not in points.keys() else points[point] + 1

    elif y1 == y2: # horizontal line
        start_point = f"{x1},{y1}"
        points[start_point] = 1 if start_point not in points.keys() else points[start_point] + 1
        temp = x1
        while temp != x2:
            temp = temp + 1 if temp < x2 else temp - 1
            point = f"{temp},{y1}"
            points[point] = 1 if point not in points.keys() else points[point] + 1
    elif abs(x2-x1) == abs(y2-y1): # vertical line at 45 degrees
        start_point = f"{x1},{y1}"
        points[start_point] = 1 if start_point not in points.keys() else points[start_point] + 1
        temp_x = x1
        temp_y = y1
        while temp_x != x2:
            temp_x = temp_x + 1 if temp_x < x2 else temp_x - 1
            temp_y = temp_y + 1 if temp_y < y2 else temp_y - 1
            point = f"{temp_x},{temp_y}"
            points[point] = 1 if point not in points.keys() else points[point] + 1
print(points) 
s = 0 
for i in points.keys(): 
    if points[i] > 1: s += 1 
print(s)