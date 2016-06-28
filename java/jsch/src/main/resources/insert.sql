replace into fn_jr.fn_person_info (business_id, contract_id,
      type, property, name, customer_id, customer_no,
      email, sex, nationality, 
      cardType, cardNo, identity,
      marriage, phone1, phone2, 
      phone3, address, censusRegister, 
      censusRegisterAddress, purchasPurposes, bank_account_name, bank_account_number, branch_bank,
      sellReason, onlyHouse, companyName, companyNature, jobTitle, job, education, income, create_userid, 
      update_userid, update_time, create_time,po_id,is_beijing
      )
    values ( :business_id, :contract_id,
      :type, :property, :name, :customer_id, :customer_no,
      :email, :sex, :nationality, 
      :cardType, :cardNo, :identity, 
      :marriage, :phone1, :phone2, 
      :phone3, :address, :censusRegister, 
      :censusRegisterAddress, :purchasPurposes, :bank_account_name, :bank_account_number, :branch_bank,
      :sellReason, :onlyHouse, :companyName, 
      :companyNature, :jobTitle, :job, :education,
      :income, :create_userid,
      :update_userid, now(), now(),:po_id,
      :is_beijing
      )