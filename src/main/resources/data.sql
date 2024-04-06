-- TASK_CATEGORIES
INSERT INTO TASK_CATEGORIES (CATEGORY_NAME, CATEGORY_DESCRIPTION)
VALUES
    ('Work', 'Tasks related to professional activities'),
    ('Personal', 'Tasks related to personal life, hobbies, and interests'),
    ('Education', 'Tasks related to learning and academic pursuits'),
    ('Health', 'Tasks related to physical and mental well-being');

-- TASKS
INSERT INTO TASKS (TASK_NAME, TASK_DESCRIPTION, DEADLINE, CATEGORY_ID)
VALUES
    ('Finish project proposal', 'Complete the project proposal document and send it to the supervisor', '2024-04-10 17:00:00', 1),
    ('Buy groceries', 'Purchase groceries for the week including fruits, vegetables, and dairy products', '2024-04-08 18:00:00', 2),
    ('Read book chapter', 'Read Chapter 3 of "Introduction to Machine Learning"', '2024-04-07 20:00:00', 3),
    ('Attend yoga class', NULL, '2024-04-09 09:30:00', 4),
    ('Submit monthly report', 'Prepare and submit the monthly progress report to the manager', '2024-04-15 12:00:00', 1),
    ('Write blog post', 'Draft a blog post on the topic of artificial intelligence in healthcare', '2024-04-12 15:00:00', 1),
    ('Call mom', 'Check in with mom and discuss plans for the weekend', '2024-04-10 19:00:00', 2),
    ('Study for exam', 'Review notes and practice problems for the upcoming statistics exam', '2024-04-14 10:00:00', 3),
    ('Go for a run', 'Complete a 5km run in the park', '2024-04-11 07:00:00', 4),
    ('Update budget spreadsheet', 'Record recent expenses and update budget projections', '2024-04-13 12:30:00', 1);