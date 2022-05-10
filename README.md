# ms-email-reader
Read Microsoft Outlook email using Graph API

Basic command-line application illustrating use of the Microsoft Graph API to scan unread mail in the authenticated user's messages and downloading any file attachments to a predefined directory before marking the mail as 'read'.

It is assumed you have registered an application in Azure and granted the appropriate permissions via API Permissions, to allow your instance of the application to talk to relevant bits of Microsoft's Graph API. Note: This solution needs Application permissions, not Delegated permissions, as it runs as a daemon, and not a user-app.
