CREATE ROLE zfgcadmin SUPERUSER;
GRANT zfgcadmin TO postgres WITH ADMIN OPTION;
ALTER ROLE postgres SET ROLE zfgcadmin;
