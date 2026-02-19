CREATE PROC sp_getEmployeeWithOneLevelreport
	@employeeId BIGINT
AS BEGIN
	WITH  selectEployeeWithManager_CTE  AS (
		SELECT 
			Employee.id
			, Employee.firstName
			, Employee.lastName
			,Employee.designation
			, Employee.mangerId 
		FROM Employee WHERE id = @employeeId  or mangerId = @employeeId
		UNION ALL
		SELECT 
			Employee.id
			, Employee.firstName
			, Employee.lastName
			,Employee.designation
			, Employee.mangerId 
		FROM selectEployeeWithManager_CTE 
		join Employee 
		ON selectEployeeWithManager_CTE.mangerId = Employee.id 
		
	)
	SELECT DISTINCT * FROM selectEployeeWithManager_CTE 
END