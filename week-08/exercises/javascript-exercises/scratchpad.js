const nums = [1,2,3,4,5];

nums[1000] = -1;

for (const num of nums) {
    console.log(num);
}

nums.forEach(n => {
    if (n === undefined) {
        console.log("undefined");
    } else {
        console.log(n);
    }
});