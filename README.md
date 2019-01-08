# Ransomware

Ransomware is computer malware that installs covertly on a victim's computer, executes a
cryptovirology attack that adversely affects it, and demands a ransom payment to decrypt it or
not publish it. Simple ransomware may lock the system in a way which is not difficult for a
knowledgeable person to reverse, and display a message requesting payment to unlock it. More
advanced malware encrypts the victim's files, making them inaccessible, and demands a ransom
payment to decrypt them.
While initially popular in Russia, the use of ransomware scams has grown internationally; in June
2013,security software vendor McAfee released data showing that it had collected over 250,000
unique samples of ransomware in the first quarter of 2013, more than double the number it had
obtained in the first quarter of 2012. Wide-ranging attacks involving encryption-based
ransomware began to increase through Trojans, which had procured millions of US Dollars.
File encrypting ransomware was invented and implemented by Young and Yung at Columbia
University and was presented at the 1996 IEEE Security & Privacy conference. It is called
cryptoviral extortion and isthe following 3-round protocol carried out between the attacker and
the victim:
[attacker  victim] The attacker generates a key pair and places the corresponding public key
in the malware. The malware is released.
2. [victim  attacker] To carry out the cryptoviral extortion attack, the malware generates a
random symmetric key and encrypts the victim's data with it. It uses the public key in the
malware to encrypt the symmetric key. This is known as hybrid encryption and it results in a
small asymmetric ciphertext as well as the symmetric ciphertext of the victim's data. It zeroizes
the symmetric key and the original plaintext data to prevent recovery. It puts up a message to
the user that includes the asymmetric ciphertext and how to pay the ransom. The victim sends
the asymmetric ciphertext and e-money to the attacker
3. [attacker  victim] The attacker receives the payment, deciphers the asymmetric ciphertext
with his private key, and sends the symmetric key to the victim. The victim deciphers the
encrypted data with the needed symmetric key thereby completing the cryptovirology attack 



## Project Idea:
<p align="center">
  <img width="347" height="246" src="https://serving.photos.photobox.com/85102258ab416dcc2e78dd53e19e924971aabc245f1edd4c33b97caee4fe83f25be3e7b3.jpg">
</p>
The project is composed of two parts:

### Part 1 is the victim part :
The victim part is just a greeting card like the following card.
When the victim, clicks “SEND the card to a friend”, the following happen:
1) An AES key (K1 that is composed of 128 bits) is generated randomly.
2) All files in a folder called "important files” will be encrypted using this AES key.
3) K1 is encrypted using the hacker’s RSA public key (PU). C = EPU(K1). The hacker’s public
key (PU) usually is embedded in the victim part.
4) The photo on the greeting card must be changed to a message asking the victim to send
an amount of money (ransom) to the hacker account. Below the ransom message is C
which is the encrypted key.

### Part 2 is the hacker part :
 It allows the hacker to insert C = EPU(K1) that usually the victim sends
it to the hacker by email after paying the ransom. When C is inserted, it decrypts the cipher and
retrieves the AES key K1 = DPR(C), where PR is the hacker’s private key embedded in the hacker
part. Subsequently, the hacker is supposed to send K1 to the victim by email in order for the
victim to be able to decrypt the files in the folder “Important files”.
