alter proc sp_resetCurrentCycleOfGameType
	
AS BEGIN
	with getGameNeedsToResetCurrentCycle as (
	select gameType_id from EmployeeWiseGameInterest where isInterested = 1 group by gameType_id having SUM(currentCyclesSlotConsumed) >= count(employee_id)
	)
	update EmployeeWiseGameInterest
	set currentCyclesSlotConsumed = 0
	where gameType_id in (select gameType_id from getGameNeedsToResetCurrentCycle)
END