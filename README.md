# -------------------Splitwise--------------------
### Functional Requirements
- Any user can add an expense
- User details - name, phone, email, address
- Expense details - name, date, images, notes, location
- When adding expense, one person pays for their friends
- So the amount must be split amongst friends
- Users can be added
- Various type of expenses
	- split equally amongst all
		- sanity check
	- split unequally
		- enter each amount
		- percentage
		- parts
		- constraint
- round off to 2 decimal places
- user can see their past expenses
- user can see their net amount
	- in total
	- against each friend
		- sanity for 0 values
- show total of all users

### Queries:
- add expense
	- who paid
	- people in the group
	- expense type
	- values if unequal
- show balances
- user balances
- user passbook
- simplify expenses
	-minimize number of transactions

### Input Format
- add_user name email passwordhash
- add_expense expense_type name createdBy
- add_expense expense_type name createdBy [paidBy] [user val user val]
- add_split expenseId userId
- add_split expenseId userId amount_or_percent 
- show show all balances
- show userID
