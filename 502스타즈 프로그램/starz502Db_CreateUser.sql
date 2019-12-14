create user starz identified by 1234;

grant create session to starz;

CREATE TABLESPACE starzSpace
DATAFILE 'C:\oraclexe\app\oracle\oradata\XE\starz.dbf' SIZE 10M
AUTOEXTEND ON NEXT 5M;
 
ALTER USER starz DEFAULT TABLESPACE starzSpace;

ALTER USER starz QUOTA UNLIMITED ON starzSpace;

grant connect,resource,dba to starz;