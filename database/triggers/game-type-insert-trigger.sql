CREATE trigger gameTypeInsertTrigger 
on GameType
after insert
as begin
	declare @id bigint
	select @id=inserted.id from inserted

	merge EmployeeWiseGameInterest as target
	using (
		select id as employee_id, @id as gameType_id from employee
	) as source
	on target.employee_id = source.employee_id and target.gameType_id = source.gameType_id
	WHEN NOT MATCHED THEN
	insert (createdAt,currentCyclesSlotConsumed,isBlocked,isInterested,noOfSlotConsumed,updatedAt,employee_id,gameType_id)
	values (GETDATE(),0,0,0,0,GETDATE(),source.employee_id,source.gameType_id);
end
