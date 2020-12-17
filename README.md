# Rent Collection Android App

UI Link: https://xd.adobe.com/view/f8ca4673-ef16-4899-b264-bb491b584282-ad99/

### Documentaion - Requirement Specification
Mobile App
1. Login (username, Password)
2. Manager login
3. Admin Login
4. Add Tenant
5. Add Credit
6. Add Rent
7. Add Expenses
8. Check History
9. Export data

Admin Modules:
1. Add Tenant
2. Add Credit
3. Add Rent
4. Add Expenses
5. Check History
6. Export data

Manager Modules:
1. Add Credit
2. Add Rent
3. Add Expenses
4. Check History

[1.0] Login:

A user can login using predefined credentials. There will be 2 different user roles Admin and Manager.
Admin can access Admin modules and the Manager can access manager modules.

[2.0] Add Tenant:

There will be multiple entries to register a tenant to the system. If a tenant leaves property/shop than
the admin assigns the specific id and block to a new tenant and also update the other information
accordingly. The system should save both old and new tenant data registered to the ID|Block.

Fields: ID, Block, Name, Phone, Shop name, Rent, Advance/Security

• ID: integer from 1 – 20

• Block: char (alphabets) A – J

• Name: string

• Phone: tel

• Shop name: sting

• Rent: double

• Advance/Security: double

[3.0] Add Credit:

A Credit is an ongoing ledger between Owner and Tenant. By selecting any tenant, a user can add credit
to its ledger and the credit amount would be deducted from tenant’s monthly rent.

Fields: ID, Block, Name, Credit, Description

• Credit amount: double
• Description: string

[4.0] Add Rent:

From here user can enter collected rent of a tenant by selecting it from ID|Name. It should display
tenant’s previous rent information after selection (tenant rent, tenant due, tenant advance)
Fields: ID, Block, Name, Received rent amount, Description.
• ID: integer from 1 – 20
• Block: char (alphabets) A – J
• Name: string
• Received rent amount: double
• Description: string

[5.0] Add Expenses:

This module is a total separate module from all the rent collection system. From this module the user
will log his expenses only. On this screen the system should also display current month expenses and
lifetime expenses.

Fields: Expense Amount, Expense Type, Description
• Expense amount: double
• Expense Type: string
• Description: string

[6.0] History:

From history your can see all the logs and entries performed on the system. The history module would
be different for both Admin and Manage. Admin can see all the entries and logs where as manager
would only be able to see the remaining dues of tenant

[6.1] Search:

The search can be performed by ID, Name and block

[6.2] Date Filter:

Shows the entries/logs of the selected dates only

[6.3] Collected Filter

Shows the collected rent data only

[6.4] Remaining Filter

Shows the remaining rent data only

[6.4] All Filter

Shows the entries/logs

[7.0] Export Data:

The admin can export data from mobile app that would import into the desktop app.
Desktop Application
1. Login (username, Password)
2. Dashboard
3. Add Tenant
4. Add Credit
5. Add Rent
6. Add Expenses
7. Check History
8. Import Data
9. Print reports

[1.0] Login:

An Admin can login using predefined credentials. There will be only admin user role.

[2.0] Add Tenant:

There will be multiple entries to register a tenant to the system. If tenant is already registered to specific
selected ID and Block than the system should allow Admin to update the information. The system should
save both old and new tenant data registered to the ID|Name.

Fields: Picture, ID, Block, Name, Phone, Rent, Advance/Security, Contract

• Picture: Image file attachment
• ID: integer from 1 – 20
• Block: char (alphabets) A – J
• Name: string
• Phone: tel
• Shop name: string
• Rent: double
• Advance/Security: double
• Contract: multiple image file attachments

[3.0] Add Credit:

A Credit is an ongoing ledger between Owner and Tenant. By selecting any tenant, a Admin can add
credit to its ledger and the credit amount would be deducted from tenant’s monthly rent.

Fields: ID, Block, Name, Credit, Description, Proof
• Credit amount: double
• Description: string
• Proof: multiple image file attachments

[4.0] Add Rent:

From here Admin can enter collected rent of a tenant by selecting it from ID|Name. It should display
tenant’s previous rent information after selection (tenant rent, tenant due, tenant advance)

Fields: ID, Block, Name, Received rent amount, Description, Proof
• ID: integer from 1 – 20
• Block: char (alphabets) A – J
• Name: string
• Received rent amount: double
• Description: string
• Proof: multiple image file attachments

[5.0] Add Expenses:
This module is a total separate module from all the rent collection system. From this module the user
will log his expenses only. On this screen the system should also display current month expenses and
lifetime expenses.

Fields: Expense Amount, Expense Type, Description
• Expense amount: double
• Expense Type: string
• Description: string
• Proof: multiple image file attachments

[6.0] History:

From history admin can see all the logs and entries performed on the system.

[6.1] Search:

The search can be performed by ID, Name and block

[6.2] Date Filter:

Shows the entries/logs of the selected dates only

[6.3] Collected Filter

Shows the collected rent data only

[6.4] Remaining Filter

Shows the remaining rent data only

[6.4] All Filter

Shows the entries/logs

[6.5] Expenses Filter

Shows the expenses only

[7.0] Import Data:

The admin can Import data that was exported from mobile app. The system must append the imported
data and keep both old and new entries/logs

[8.0] Print Reports:

The admin should be able to print specific period, person, id, or block reports
