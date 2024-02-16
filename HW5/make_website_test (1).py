import unittest

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

if __name__ == '__main__':
    unittest.main()
