package spinoco.protocol.email.header

import scodec.Codec
import shapeless.tag.@@
import spinoco.protocol.email.header.codec.{cfwsSeparated, msgIdCodec}

/**
  * RFC 5322 3.6.4
  *
  *  The "In-Reply-To:" and "References:" fields are used when creating a
  *  reply to a message.  They hold the message identifier of the original
  *  message and the message identifiers of other messages (for example,
  *  in the case of a reply to a message that was itself a reply).  The
  *  "In-Reply-To:" field may be used to identify the message (or
  *  messages) to which the new message is a reply, while the
  *  "References:" field may be used to identify a "thread" of
  *  conversation.
  *
  * @param msgId    Id of the message
  * @param others   Id of other messages
  */
case class References (
msgId: String @@ MessageId
, others: List[String @@ MessageId]
) extends EmailHeaderField {
  def name: String = References.name
}


object References extends HeaderDescription[References] {

  val name: String = "In-Reply-To"

  val codec: Codec[References] =
    cfwsSeparated(msgIdCodec)
      .xmap(References.apply _ tupled, { rfr => (rfr.msgId, rfr.others) })

  def fieldCodec: Codec[EmailHeaderField] = codec.upcast

}