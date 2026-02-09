CREATE PROCEDURE sp_activeRequestCount
	@employeeId [bigint]
AS BEGIN
	SELECT count(*) 
	FROM SlotRequest 
	JOIN GameSlot
	ON GameSlot.id = SlotRequest.gameSlot_id
	WHERE SlotRequest.status = 'On hold' and SlotRequest.requestedBy_id = @employeeId and Day(GameSlot.slotDate) = DAY(GETDATE())
END

EXECUTE sp_activeRequestCount 1