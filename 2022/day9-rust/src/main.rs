use std::collections::HashSet;

#[derive(Eq, Copy, Clone, Hash)]
struct Pair {
    x: i32,
    y: i32,
}

impl Pair {
    fn new() -> Self {
        Self { x: 0, y: 0 }
    }

    fn move_direction(&mut self, direction: char) {
        match direction {
            'U' => self.x += 1,
            'D' => self.x -= 1,
            'L' => self.y -= 1,
            'R' => self.y += 1,
            _ => (),
        }
    }

    fn is_close_to_pair(&self, other: &Pair) -> bool {
        (self.x - other.x).abs() < 2 && (self.y - other.y).abs() < 2
    }
}

impl PartialEq for Pair {
    fn eq(&self, other: &Self) -> bool {
        self.x == other.x && self.y == other.y
    }
}

fn main() {
    let input = read_input("input.txt");
    use std::time::Instant;
    let now = Instant::now();
    {
        println!("Part 1: {}", solve_part_1(&input));
        println!("Part 2: {}", solve_part_2(&input));
    }
    let elapsed = now.elapsed();
    println!("Duration: {:.0?}", elapsed)
}

#[inline(always)]
fn read_input(path: &str) -> String {
    use std::fs;
    fs::read_to_string(path).unwrap()
}

fn solve_part_1(input: &str) -> usize {
    let mut result = HashSet::new();
    let mut tail = Pair::new();
    let mut head = Pair::new();
    result.insert(tail.clone());
    for line in input.lines() {
        let instructions = line.split_ascii_whitespace().collect::<Vec<&str>>();
        let dir = instructions[0];
        let parsed_count = instructions[1].parse::<i32>().unwrap();
        for _ in 0..parsed_count {
            head.move_direction(dir.chars().next().unwrap());
            tail = follow(tail, head);
            result.insert(tail.clone());
        }
    }
    result.len()
}

fn solve_part_2(input: &str) -> usize {
    let mut result = HashSet::new();
    let mut rope = vec![Pair::new(); 10];
    result.insert(Pair::new());
    for line in input.lines() {
        let instructions = line.split_ascii_whitespace().collect::<Vec<&str>>();
        let dir = instructions[0];
        let parsed_count = instructions[1].parse::<i32>().unwrap();
        for _ in 0..parsed_count {
            rope[0].move_direction(dir.chars().next().unwrap());
            for i in 1..10 {
                rope[i] = follow(rope[i], rope[i - 1]);
            }
            result.insert(rope[9]);
        }
    }
    result.len()
}

#[inline(always)]
fn follow(tail: Pair, head: Pair) -> Pair {
    let mut result = tail.clone();
    if !result.is_close_to_pair(&head) {
        if head.x != tail.x && head.y != tail.y {
            let current_x = result.x;
            let current_y = result.y;
            if head.x > current_x {
                result.move_direction('U');
            } else {
                result.move_direction('D');
            }
            if head.y > current_y {
                result.move_direction('R');
            } else {
                result.move_direction('L');
            }
        }
        if (head.x - result.x).abs() > 1 {
            if head.x > result.x {
                result.move_direction('U');
            } else {
                result.move_direction('D');
            }
        }
        if (head.y - result.y).abs() > 1 {
            if head.y > result.y {
                result.move_direction('R');
            } else {
                result.move_direction('L');
            }
        }
    }
    result
}