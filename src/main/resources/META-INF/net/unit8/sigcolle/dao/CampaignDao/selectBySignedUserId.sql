SELECT campaign.campaign_id,campaign.title,campaign.statement,campaign.goal,campaign.created_by FROM campaign INNER JOIN signature on campaign.campaign_id = signature.campaign_id WHERE signature.signed_by=/*userId*/1;