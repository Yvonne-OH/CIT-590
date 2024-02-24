import unittest
from io import StringIO
from make_website import *

class MakeWebsite_Test(unittest.TestCase):

    def test_surround_block(self):
        # test text with surrounding h1 tags
        self.assertEqual("<h1>Eagles</h1>", surround_block('h1', 'Eagles'))

        # test text with surrounding h2 tags
        self.assertEqual("<h2>Red Sox</h2>", surround_block('h2', 'Red Sox'))

        # test text with surrounding p tags
        self.assertEqual('<p>Lorem ipsum dolor sit amet, consectetur ' +
                         'adipiscing elit. Sed ac felis sit amet ante porta ' +
                         'hendrerit at at urna.</p>',
                         surround_block('p', 'Lorem ipsum dolor sit amet, consectetur ' +
                                        'adipiscing elit. Sed ac felis sit amet ante porta ' +
                                        'hendrerit at at urna.'))

    def test_create_email_link(self):

        # test email with @ sign
        self.assertEqual(
            '<a href="mailto:lbrandon@wharton.upenn.edu">lbrandon[aT]wharton.upenn.edu</a>',
            create_email_link('lbrandon@wharton.upenn.edu')
        )

        # test email with @ sign
        self.assertEqual(
            '<a href="mailto:krakowsky@outlook.com">krakowsky[aT]outlook.com</a>',
            create_email_link('krakowsky@outlook.com')
        )

        # test email without @ sign
        self.assertEqual(
            '<a href="mailto:lbrandon.at.seas.upenn.edu">lbrandon.at.seas.upenn.edu</a>',
            create_email_link('lbrandon.at.seas.upenn.edu')
        )
    
    def test_extract_name(self):
        # Test with valid name
        self.assertEqual(extract_name(["John Doe\n", "Second Line"]), "John Doe")
        # Test with invalid name (not starting with uppercase)
        self.assertEqual(extract_name(["john Doe\n"]), "Invalid Name")
        # Test with empty list
        self.assertEqual(extract_name([]), "Invalid Name")
        # Test with whitespace before and after name
        self.assertEqual(extract_name(["  John Doe  \n"]), "John Doe")

    def test_extract_email(self):
        # Test with valid email in the list
        self.assertEqual(extract_email(["john.doe@example.com", "Second Line"]), "john.doe@example.com")
        # Test with multiple valid emails, returns the first valid one
        self.assertEqual(extract_email(["First Line", "john.doe@example.com", "jane.doe@example.edu"]), "john.doe@example.com")
        # Test with no valid email
        self.assertEqual(extract_email(["First Line", "Not An Email", "Also Not An Email"]), "")
        # Test with email having digits (considered invalid based on the given logic)
        self.assertEqual(extract_email(["john.doe1@example.com"]), "")
        # Test with domain part not having any lowercase letters (considered invalid based on the given logic)
        self.assertEqual(extract_email(["JOHN.DOE@EXAMPLE.COM"]), "")
        
    def test_extract_courses(self):
        # Test with a valid courses line
        file_lines = [
            "Courses:- Math, Science, English",
            "Other line"
        ]
        self.assertEqual(extract_courses(file_lines), ["Math", "Science", "English"])

        # Test with no valid courses line
        file_lines = [
            "No courses listed here"
        ]
        self.assertEqual(extract_courses(file_lines), [])

        # Test with courses string empty after ":-"
        file_lines = [
            "Courses:-"
        ]
        self.assertEqual(extract_courses(file_lines), [])

    def test_extract_projects(self):
        # Test with valid projects listed
        file_lines = [
            "Projects",
            "Project 1: A",
            "Project 2: B",
            "----------"
        ]
        self.assertEqual(extract_projects(file_lines), ["Project 1: A", "Project 2: B"])

        # Test with no "Projects" marker
        file_lines = [
            "No projects listed here"
        ]
        self.assertEqual(extract_projects(file_lines), [])

        # Test with projects but no end marker
        file_lines = [
            "Projects",
            "Project 1: A",
            "Project 2: B"
        ]
        self.assertEqual(extract_projects(file_lines), ["Project 1: A", "Project 2: B"])

        # Test with blank lines between project entries
        file_lines = [
            "Projects",
            "Project 1: A",
            "",
            "Project 2: B",
            "----------"
        ]
        self.assertEqual(extract_projects(file_lines), ["Project 1: A", "Project 2: B"])
        
    def setUp(self):
        self.mock_file = StringIO()  # Use StringIO to mock a file object

    def test_surround_block(self):
        result = surround_block('p', 'Hello, World!')
        self.assertEqual(result, '<p>Hello, World!</p>')

    def test_start_page_wrap(self):
        start_page_wrap(self.mock_file)
        self.assertEqual(self.mock_file.getvalue(), '<div id="page-wrap">\n')

    def test_write_basic_info(self):
        write_basic_info(self.mock_file, 'John Doe', 'john@doe.com')
        expected_output = '<div>\n<h1>John Doe</h1>\n<p>Email: <a href="mailto:john@doe.com">john[aT]doe.com</a></p>\n</div>\n'
        self.assertEqual(self.mock_file.getvalue(), expected_output)

    def test_write_projects(self):
        write_projects(self.mock_file, ['Project 1', 'Project 2'])
        expected_output = '<div>\n<h2>Projects</h2>\n<ul>\n<li>Project 1</li>\n<li>Project 2</li>\n</ul>\n</div>\n'
        self.assertEqual(self.mock_file.getvalue(), expected_output)

    def test_write_courses(self):
        write_courses(self.mock_file, ['Course 1', 'Course 2'])
        


if __name__ == '__main__':
    unittest.main()
