"""
In this assignment, you will write a program that calculates a dog’s age in human years.
The program will prompt the user for an age in dog years and calculate that age in human years.
Allow for int or float values, but check the user’s input to make sure it's valid -- it should be numeric and positive.
Otherwise, let the user know their input is not valid.

You can use the following rules to approximately convert a medium-sized dog’s age to human years:
- For the first year, one dog year is equal to 15 human years
- For the first 2 years, each dog year is equal to 12 human years
- For the first 3 years, each dog year is equal to 9.3 human years
- For the first 4 years, each dog year is equal to 8 human years
- For the first 5 years, each dog year is equal to 7.2 human years
- After that, each dog year is equal to 7 human years.  (Note: This means the first 5 dog years are
equal to 36 human years (5 * 7.2) and the remaining dog years are equal to 7 human years each.)

Print the result in the following format, substituting for <dog_age> and <human_age>: "The given dog age <dog_age>
is <human_age> in human years."  Round the result to 2 decimal places.  Note: If there is a 0 in the hundredths place,
you can drop it, e.g. 24.00 can be displayed as 24.0.

"""

# Get dog age
age = input("Input dog years: ")

try:
    # Cast to float
    d_age = float(age)

    # ADD YOUR CODE HERE
    # If user enters negative number, print message
    # Otherwise, calculate dog's age in human years



except:
    print(age, "is invalid.")
