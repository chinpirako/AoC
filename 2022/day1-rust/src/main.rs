fn main() {
    let input = read_input("input.txt");
    use std::time::Instant;
    let now = Instant::now();
    {
        println!("Part 1: {}", solve_part_1(&input));
        println!("Part 2: {}", solve_part_2(&input));
    }
    let elapsed = now.elapsed();
    println!("Duration: {:.2?}", elapsed)
}

#[inline(always)]
fn read_input(path: &str) -> Vec<Vec<i64>> {
    use std::fs;

    let contents = fs::read_to_string(path).expect("Cannot read to string for some ungodly reason");
    let mut result: Vec<Vec<i64>> = Vec::new();
    let mut row: Vec<i64> = Vec::new();
    for line in contents.lines() {
        if line.len() > 0 {
            row.push(line.parse::<i64>().unwrap());
        } else {
            result.push(row.clone());
            row.clear();
        }
    }
    result
}


#[inline(always)]
fn solve_part_1(input: &Vec<Vec<i64>>) -> i64 {
    *get_sum(input).iter().max().unwrap()
}

#[inline(always)]
fn solve_part_2(input: &Vec<Vec<i64>>) -> i64 {
    let mut sum: Vec<i64> = get_sum(input);
    // Sort and reverse the vector
    sum.sort();
    sum.reverse();
    sum[0..3].iter().sum()
}

#[inline(always)]
fn get_sum(input: &Vec<Vec<i64>>) -> Vec<i64> {
    let mut result: Vec<i64> = Vec::new();
    for row in input {
        result.push(row.clone().iter().sum());
    }
    result
}