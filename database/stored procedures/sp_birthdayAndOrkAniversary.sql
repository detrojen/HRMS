alter proc sp_birthdayAndOrkAniversary
as begin
SET  NOCOUNT ON
MERGE Post AS target
  
    USING (
        SELECT 
            e.Id AS EmployeeId,
            CONCAT('Happy Birthday ', e.FirstName, '!') AS body,
            CAST(GETDATE() AS DATE) AS createdAt
        FROM Employee e
        WHERE 
            MONTH(e.Dob) = MONTH(GETDATE())
            AND DAY(e.Dob) = DAY(GETDATE())
    ) AS source
    ON target.body = source.body
       AND target.createdAt = source.createdAt

    WHEN NOT MATCHED THEN
        INSERT (title, body, CreatedAt,updatedAt, tags,attachmentPath,commentCount,likeCount,isDeleted,isDeletedByHr)
        VALUES ('Happy birthday',source.body,source.createdAt, source.createdAt,'Happy birthaday','birthady.png',0,0,0,0);

MERGE Post AS target
    USING (
        SELECT 
            e.Id AS EmployeeId,
            CONCAT('Happy work aniversary ', e.FirstName, '! consgratulations', e.firstName , ' has completed ', DATEDIFF(YEAR,joinedAt,GETDATE()),' years') AS body,
            CAST(GETDATE() AS DATE) AS createdAt
        FROM Employee e
        WHERE 
            MONTH(e.joinedAt) = MONTH(GETDATE())
            AND DAY(e.joinedAt) = DAY(GETDATE())
    ) AS source
    ON target.body = source.body
       AND target.createdAt = source.createdAt

    WHEN NOT MATCHED THEN
        INSERT (title, body, CreatedAt,updatedAt, tags,attachmentPath,commentCount,likeCount,isDeleted,isDeletedByHr)
        VALUES ('Happy work aniversary',source.body,source.createdAt, source.createdAt,'work aniversary','work_aniversary.png',0,0,0,0);
select 1
end