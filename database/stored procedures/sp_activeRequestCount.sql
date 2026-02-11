ALTER PROCEDURE sp_activeRequestCount
	@employeeId [bigint],
	@gameTypeId [bigint]
AS BEGIN
	SELECT count(*)
	FROM SlotRequest 
	JOIN GameSlot
	ON GameSlot.id = SlotRequest.gameSlot_id
	WHERE SlotRequest.requestedBy_id = @employeeId
		and GameSlot.gameType_id = @gameTypeId
		and SlotRequest.status in ( 'Confirm', 'On hold') and FORMAT(slotDate,'yyyy-MM-dd') = FORMAT(GETDATE(),'yyyy-MM-dd') and GameSlot.startsFrom > FORMAT(GETDATE(),'hh:mm:ss') 
END

EXECUTE sp_activeRequestCount 2,1