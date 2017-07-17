SPOOL .\MPInstall.log

@@createTables.sql
@@createAutoIncrement.sql
@@createUsersAITrigger.sql
@@createBidsAITrigger.sql
@@createItemsAITrigger.sql
@@fillData.sql

SPOOL OFF