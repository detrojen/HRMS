create trigger tr_UpSlotrequest
ON SlotRequest 
AFTER UPDATE 
AS BEGIN
	SET NOCOUNT ON
	DECLARE @newStatus varchar(12)
	DECLARE @oldStatus varchar(12)
	DECLARE @slotReqId BIGINT
	SELECT @slotReqId=inserted.id, @newStatus = inserted.status from inserted
	SELECT  @oldStatus = deleted.status from deleted

	BEGIN IF(@newStatus = 'Confirm')

		update EmployeeWiseGameInterest
		set currentCyclesSlotConsumed = currentCyclesSlotConsumed + 1
			,noOfSlotConsumed = noOfSlotConsumed + 1
		where EmployeeWiseGameInterest.employee_id in (
			select 
				sre.employee_id 
			from SlotRequestWiseEmployee sre
			where sre.slotRequest_id = @slotReqId
		)
	END
	BEGIN IF(@oldStatus = 'Confirm' AND (@newStatus='On hold' OR @newStatus='cancel'))
		update EmployeeWiseGameInterest
		set currentCyclesSlotConsumed = currentCyclesSlotConsumed - 1
			,noOfSlotConsumed = noOfSlotConsumed - 1
		where EmployeeWiseGameInterest.employee_id in (
			select 
				sre.employee_id 
			from SlotRequestWiseEmployee sre
			where sre.slotRequest_id = @slotReqId
		)
	END

END