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

fn read_input(path: &str) -> Vec<Vec<usize>> {
    use std::fs;
    let content = fs::read_to_string(path).unwrap();
    let mut result = Vec::new();
    content.lines().for_each(|line| {
        let mut row = Vec::new();
        line.chars().for_each(|c| row.push(c.to_digit(10).unwrap() as usize));
        result.push(row);
    });
    result
}

fn solve_part_1(input: &Vec<Vec<usize>>) -> usize {
    let height = input.len();
    let width = input[0].len();

    let mut result = 2 * (height + width) - 4;
    for i in 1..(height - 1) {
        for j in 1..(width - 1) {
            let current_tree = input[i][j];
            if input[i][0..j].iter().all(|&x| x < current_tree)
                || input[i][j + 1..width].iter().all(|&x| x < current_tree)
                || input[0..i].iter().all(|row| row[j] < current_tree)
                || input[i + 1..height].iter().all(|row| row[j] < current_tree) {
                result += 1;
            }
        }
    }
    result
}

fn solve_part_2(input: &Vec<Vec<usize>>) -> usize {
    let height = input.len();
    let width = input[0].len();
    let mut result = 0;

    for i in 1..(height - 1) {
        for j in 1..(width - 1) {
            let current_tree = input[i][j];
            let (mut left_count,
                mut right_count,
                mut up_count,
                mut down_count) = (0, 0, 0, 0);
            for k in (0..j).rev() {
                left_count += 1;
                if input[i][k] >= current_tree {
                    break;
                }
            }
            for k in (j + 1)..width {
                right_count += 1;
                if input[i][k] >= current_tree {
                    break;
                }
            }
            for k in (0..i).rev() {
                up_count += 1;
                if input[k][j] >= current_tree {
                    break;
                }
            }
            for k in (i + 1)..height {
                down_count += 1;
                if input[k][j] >= current_tree {
                    break;
                }
            }
            let tmp = left_count * right_count * up_count * down_count;
            result = if tmp > result { tmp } else { result };
        }
    }
    result
}
