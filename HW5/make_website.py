def surround_block(tag, text):
    """
    Surrounds the given text with the given html tag and returns the string.
    """

    # insert code
    return f'<{tag}>{text}</{tag}>'

def create_email_link(email_address):
    """
    Creates an email link with the given email_address.
    To cut down on spammers harvesting the email address from the webpage,
    displays the email address with [aT] instead of @.

    Example: Given the email address: lbrandon@wharton.upenn.edu
    Generates the email link: <a href="mailto:lbrandon@wharton.upenn.edu">lbrandon[aT]wharton.upenn.edu</a>

    Note: If, for some reason the email address does not contain @,
    use the email address as is and don't replace anything.
    """

    # insert code
    # Check if the email address contains an '@'
    if '@' in email_address:
        display_email = email_address.replace('@', '[aT]')
    else:
        display_email = email_address

    return f'<a href="mailto:{email_address}">{display_email}</a>'

def generate_html(txt_input_file, html_output_file):
    """
    Loads given txt_input_file,
    gets the name, email address, list of projects, and list of courses,
    then writes the info to the given html_output_file.

    # Hint(s):
    # call function(s) to load given txt_input_file
    # call function(s) to get name
    # call function(s) to get email address
    # call function(s) to get list of projects
    # call function(s) to get list of courses
    # call function(s) to write the name, email address, list of projects, and list of courses to the given html_output_file
    """

    # insert code
    input_file=read_file(txt_input_file)
    print (input_file)
    name = extract_name(input_file)
    email = extract_email(input_file)
    courses = extract_courses(input_file)
    projects = extract_projects(input_file)
    
    with open(html_output_file, 'w') as file:
       start_page_wrap(file)
       write_basic_info(file, name, email)
       write_projects(file, projects)
       write_courses(file, courses)
       end_page(file)
    pass

def read_file(file_path):
    """
    Reads a file and returns its content as a list of lines.
    """
    with open(file_path, 'r') as file:
        return file.readlines()
    
def extract_name(file_lines):
    """
    Extracts the name from the first line of the file.
    If the first character is not an uppercase letter, returns 'Invalid Name'.
    Removes leading or trailing whitespace.
    """
    if not file_lines:  # Check if the file_lines list is empty
        print("Empty File!")
        return 'Invalid Name'
    
    name = file_lines[0].strip()  # Remove leading/trailing whitespace
    
    # Check if the first character is uppercase and the name is not empty
    if name and name[0].isupper():
        print ("Name:", name)
        return name
    else:
        print ("Not Uppercase Letter Name!")
        return 'Invalid Name'
    
def extract_email(file_lines):
    """
    Detect and return the email address
    """
    for line in file_lines:
        email = line.strip()                                     # Remove leading/trailing whitespace
        if '@' in email and (email.endswith('.com') or email.endswith('.edu')):
            at_index = email.index('@')
            local_part = email[:at_index]
            domain_part = email[at_index+1:]
            if any(char.isdigit() for char in email):            # Check for digits
                continue                                         # Skip if there are digits
            if not any(char.islower() for char in domain_part):  # Check for lowercase after '@'
                continue                                         # Skip if there are digits
            print("email: ",email)
            return email
    return ''                                                # Return empty string 

def extract_courses(file_lines):
    """
    Detect and return the courses 
    """
    courses_list = []
    for line in file_lines:
        normalized_line = line.strip()
        if 'courses' in normalized_line.lower():
            parts = normalized_line.split(":-", 1)              # Split the line at ":-"
            if len(parts) > 1:
                courses_str = parts[1].lstrip(' _-#$&^!*()')    # Remove leading non-alphabetical characters manually
                                                                # Find the index where the first alphabetical character occurs
                idx = next((i for i, char in enumerate(courses_str) if char.isalpha()), None)
                if idx is not None:
                    courses_str = courses_str[idx:]             # Split the courses part by commas and strip each course name             
                courses = [course.strip() for course in courses_str.split(',') if course.strip()]
                courses_list = [course for course in courses if course[0].isalpha()]    # Ensure each course name starts with an alphabetic character
                return courses_list
    return []  # Return an empty list if no courses are found
    
            
def extract_projects(file_lines):
    """
    Detects and returns the projects as a list from the file lines.
    """
    projects = []
    record = False                    # Flag to start recording projects
    for line in file_lines:
        if 'Projects' in line:
            record = True             # Projects exist
            continue
        if record:
            if '----------' in line:  # Check for at least 10 minus signs
                break                 # End of projects section
            if line.strip() == '':    # Ignore blank lines
                continue
            projects.append(line.strip())
    print("Projects:", projects)
    return projects

def surround_block(tag, text):
    return f'<{tag}>{text}</{tag}>'

def start_page_wrap(file):
    file.write('<div id="page-wrap">\n')

def write_basic_info(file, name, email):
    file.write('<div>\n')
    file.write(surround_block('h1', name) + '\n')
    email_link = create_email_link(email)
    file.write(surround_block('p', 'Email: ' + email_link) + '\n')
    file.write('</div>\n')

def write_projects(file, projects):
    file.write('<div>\n')
    file.write(surround_block('h2', 'Projects') + '\n')
    file.write('<ul>\n')
    for project in projects:
        file.write(surround_block('li', project) + '\n')
    file.write('</ul>\n')
    file.write('</div>\n')

def write_courses(file, courses):
    file.write('<div>\n')
    file.write(surround_block('h3', 'Courses') + '\n')
    courses_str = ', '.join(courses)  # Join the list of courses into a single string
    file.write(surround_block('span', courses_str) + '\n')
    file.write('</div>\n')

def end_page(file):
    file.write('</div>\n</body>\n</html>')




def main():

    # DO NOT REMOVE OR UPDATE THIS CODE
    # generate resume.html file from provided sample resume.txt
    generate_html('resume.txt', 'resume.html')

    # DO NOT REMOVE OR UPDATE THIS CODE.
    # Uncomment each call to the generate_html function when you’re ready
    # to test how your program handles each additional test resume.txt file
    generate_html('TestResumes/resume_bad_name_lowercase/resume.txt', 'TestResumes/resume_bad_name_lowercase/resume.html')
    generate_html('TestResumes/resume_courses_w_whitespace/resume.txt', 'TestResumes/resume_courses_w_whitespace/resume.html')
    generate_html('TestResumes/resume_courses_weird_punc/resume.txt', 'TestResumes/resume_courses_weird_punc/resume.html')
    generate_html('TestResumes/resume_projects_w_whitespace/resume.txt', 'TestResumes/resume_projects_w_whitespace/resume.html')
    generate_html('TestResumes/resume_projects_with_blanks/resume.txt', 'TestResumes/resume_projects_with_blanks/resume.html')
    generate_html('TestResumes/resume_template_email_w_whitespace/resume.txt', 'TestResumes/resume_template_email_w_whitespace/resume.html')
    generate_html('TestResumes/resume_wrong_email/resume.txt', 'TestResumes/resume_wrong_email/resume.html')

    # If you want to test additional resume files, call the generate_html function with the given .txt file
    # and desired name of output .html file

if __name__ == '__main__':
    main()