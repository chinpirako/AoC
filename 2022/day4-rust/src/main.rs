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
fn read_input(path: &str) -> Vec<String> {
    use std::fs;
    let content = fs::read_to_string(path).unwrap();
    let mut result = Vec::new();
    content.lines().for_each(|x| result.push(x.to_string()));
    result
}

#[inline(always)]
fn solve_part_1(input: &Vec<String>) -> u32 {
    let mut result = 0;
    input.iter().for_each(|x| {
        let x = x.split(",").collect::<Vec<&str>>();
        let x1 = x[0].split("-").map(|x| x.parse::<u16>().unwrap()).collect::<Vec<u16>>();
        let x2 = x[1].split("-").map(|x| x.parse::<u16>().unwrap()).collect::<Vec<u16>>();
        result += if x1[0] <= x2[0] && x2[1] <= x1[1] || x2[0] <= x1[0] && x1[1] <= x2[1] { 1 } else { 0 };
    });
    result
}

#[inline(always)]
fn solve_part_2(input: &Vec<String>) -> u32 {
    let mut result = 0;
    input.iter().for_each(|x| {
        let x = x.split(",").collect::<Vec<&str>>();
        let x1 = x[0].split("-").map(|x| x.parse::<u16>().unwrap()).collect::<Vec<u16>>();
        let x2 = x[1].split("-").map(|x| x.parse::<u16>().unwrap()).collect::<Vec<u16>>();
        result += if x1[0] <= x2[1] && x1[1] >= x2[0] { 1 } else { 0 };
    });
    result
}
