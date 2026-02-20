ALTER PROCEDURE sp_activeRequestCount
	@employeeId [bigint],
	@gameTypeId [bigint],
	@reqDate [date]
AS BEGIN
	SELECT COUNT(*)
	FROM SlotRequest 
	JOIN GameSlot
	ON GameSlot.id = SlotRequest.gameSlot_id
	WHERE SlotRequest.requestedBy_id = @employeeId
		and GameSlot.gameType_id = @gameTypeId
		and SlotRequest.status in ( 'Confirm', 'On hold') 
		and GameSlot.slotDate = @reqDate
		and (
			
			@reqDate > FORMAT(GETDATE(),'yyyy-MM-dd') or GameSlot.startsFrom > FORMAT(GETDATE(),'HH:mm:ss')
		)
END