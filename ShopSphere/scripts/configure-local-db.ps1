# Dot-source this script to set database variables only in the current PowerShell session.
$env:SHOPSPHERE_DB_URL = 'jdbc:mysql://localhost:3306/shopsphere'
$env:SHOPSPHERE_DB_USER = Read-Host 'MySQL username'
$securePassword = Read-Host 'MySQL password' -AsSecureString
$passwordPointer = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($securePassword)
try {
    $env:SHOPSPHERE_DB_PASSWORD = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($passwordPointer)
}
finally {
    [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($passwordPointer)
}
Write-Host 'ShopSphere database variables are set for this PowerShell session.'
